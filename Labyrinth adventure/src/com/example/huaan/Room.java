package com.example.huaan;
public class Room {
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private boolean visited;


    public Room() {
        this.north = false;
        this.south = false;
        this.east = false;
        this.west = false;
        this.visited = false;
    }

    public boolean isNorth() {
        return north;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public boolean isSouth() {
        return south;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public boolean isEast() {
        return east;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public boolean isWest() {
        return west;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


}
