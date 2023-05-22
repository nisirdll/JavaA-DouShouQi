package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;
    private CellType type;
    private String address;


    public ChessPiece(PlayerColor owner, String name, int rank,CellType type,String address) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
        this.type = type;
        this.address = address;
    }
    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }
    public CellType getType(){return type;}
    public boolean canCapture(ChessPiece target) {
        if (target == null) {
            return false;
        }

        // TODO: Finish this method!
        if (this.rank >= target.rank && (this.rank != 8 || target.rank != 1)) {
            return true;
        }
        if (this.rank == 1 && target.rank == 8) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
    public  void setRank(int rank){
        this.rank=rank;
    }
    public String getAddress() {
        return address;
    }
}
