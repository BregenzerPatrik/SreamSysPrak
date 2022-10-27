package command;

public class Position implements Comparable<Position>{
    private double x;
    private double y;
    private double z;
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }



    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isZeroMove(){

       return (this.getX()== 0) && (this.getY()==0) && (this.getZ()==0);
    }

    @Override
    public int compareTo(Position otherPosition){

        if(this.getX() != otherPosition.getX() ){
            if (this.getX() > otherPosition.getX())
                return 1;
            if (this.getX() < otherPosition.getX())
                return -1;
        }
        if(this.getY() != otherPosition.getY() ){
            if (this.getY() > otherPosition.getY())
                return 1;
            if (this.getY() < otherPosition.getY())
                return -1;
        }
        if(this.getZ() != otherPosition.getZ() ){
            if (this.getZ() > otherPosition.getZ())
                return 1;
            if (this.getZ() < otherPosition.getZ())
                return -1;
        }
        return 0;


    }
    public Position add(Position position){
        return new Position(this.getX()+position.getX(),this.getY()+position.getY(),this.getZ()+position.getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Position)){
            return false;
        }
        Position other = (Position) obj;
        return this.compareTo(other)==0;
    }
}
