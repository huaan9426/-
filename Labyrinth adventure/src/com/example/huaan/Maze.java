package com.example.huaan;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Maze {
    private int width;
    private int height;
    private Room[][] rooms;
    private List<Path> paths;
    private int playerX;
    private int playerY;
    private int exitX;
    private int exitY;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.rooms = new Room[width][height];
        this.paths = new ArrayList<>();
        this.playerX = 0;
        this.playerY = 0;
        this.exitX = width - 1;
        this.exitY = height - 1;

        // 初始化房间
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rooms[i][j] = new Room();
            }
        }

        // 使用Prim算法生成迷宫
        primMaze();
    }

    private void primMaze() {
        Random random = new Random();
        boolean[][] visited = new boolean[width][height];
        List<Path> walls = new ArrayList<>();

        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        visited[startX][startY] = true;

        addWalls(walls, startX, startY);

        while (!walls.isEmpty()) {
            int randomIndex = random.nextInt(walls.size());
            Path wall = walls.remove(randomIndex);

            int x1 = wall.getX1();
            int y1 = wall.getY1();
            int x2 = wall.getX2();
            int y2 = wall.getY2();

            if (!visited[x2][y2]) {
                visited[x2][y2] = true;
                addWalls(walls, x2, y2);

                if (x1 == x2) {
                    if (y1 < y2) {
                        rooms[x1][y1].setSouth(true);
                        rooms[x2][y2].setNorth(true);
                    } else {
                        rooms[x1][y1].setNorth(true);
                        rooms[x2][y2].setSouth(true);
                    }
                } else {
                    if (x1 < x2) {
                        rooms[x1][y1].setEast(true);
                        rooms[x2][y2].setWest(true);
                    } else {
                        rooms[x1][y1].setWest(true);
                        rooms[x2][y2].setEast(true);
                    }
                }

                paths.add(wall);
            }
        }
    }

    private void addWalls(List<Path> walls, int x, int y) {
        if (x > 0 &&!rooms[x - 1][y].isVisited()) {
            walls.add(new Path(x, y, x - 1, y));
        }
        if (x < width - 1 &&!rooms[x + 1][y].isVisited()) {
            walls.add(new Path(x, y, x + 1, y));
        }
        if (y > 0 &&!rooms[x][y - 1].isVisited()) {
            walls.add(new Path(x, y, x, y - 1));
        }
        if (y < height - 1 &&!rooms[x][y + 1].isVisited()) {
            walls.add(new Path(x, y, x, y + 1));
        }
    }

    public void movePlayer(String direction) {
        switch (direction) {
            case "north":
                if (playerY > 0 && rooms[playerX][playerY].isNorth()) {
                    playerY--;
                }
                break;
            case "south":
                if (playerY < height - 1 && rooms[playerX][playerY].isSouth()) {
                    playerY++;
                }
                break;
            case "east":
                if (playerX < width - 1 && rooms[playerX][playerY].isEast()) {
                    playerX++;
                }
                break;
            case "west":
                if (playerX > 0 && rooms[playerX][playerY].isWest()) {
                    playerX--;
                }
                break;
            default:
                System.out.println("Invalid direction!");
        }
    }

    public boolean isPlayerAtExit() {
        return playerX == exitX && playerY == exitY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public Room[][] getRooms() {
        return rooms;
    }
}
