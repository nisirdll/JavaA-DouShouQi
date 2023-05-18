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
    public CellType getType(){return type;}
    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if (this.rank>=target.rank){
            return true;
        }
        else if (this.getName().equals("Cat")&&target.getName().equals("Elephant")) {
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
