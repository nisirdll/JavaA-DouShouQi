package controller;


import listener.GameListener;
import model.*;
import view.AnimalChessComponent;
import view.CellComponent;
import view.ChessboardComponent;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {
    private final Chessboard chessboard; // 当前棋盘信息

    private PlayerColor winner = null;
    private Chessboard model;
    private ChessboardComponent view;
    public PlayerColor currentPlayer;

    public int AI=0;
    private int count = 1;
    private AnimalChessComponent selectedPieceComponent;
    public ArrayList<ChessboardPoint> canStepPoints;

    public int getCount() {
        return count;
    }
    private boolean isPlayback;

    public void setCount(int count) {
        this.count = count;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model,Chessboard chessboard) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.chessboard = chessboard;
        // Register the controller to the view
        // so that the view can call the controller's method
        // when the view receives the user's request
        // (in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece())


        view.registerController(this);
        // Initialize the chessboard
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

    }

    private void initialize() {
        //这是一个初始化的private 函数
        //
//        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
//            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
//            }
//        }
        model.initPieces();
    }

    // after a valid move swap the player
//    private void swapColor() {
//        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
//        //use this method to change the player's color,since the player is changed
//    }
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if(currentPlayer==PlayerColor.BLUE) {
            count++;

        }
    }


    public int win() {
        // TODO: Check the board if there is a winner
        ChessboardPoint redDen= new ChessboardPoint(0,3);
        ChessboardPoint blueDen= new ChessboardPoint(8,3);

        if(view.getGridComponentAt(redDen).getComponents().length!=0){
            return 1;
        }else if (view.getGridComponentAt(blueDen).getComponents().length!=0){
            return 2;
        }else {
            boolean redLose= true;
            boolean blueLose= true;
            for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                    if(model.getGrid()[i][j].getPiece()!=null){
                        ChessPiece piece= model.getGrid()[i][j].getPiece();
                        int count=0;
                        for(int k=0;k<Constant.CHESSBOARD_ROW_SIZE.getNum();k++) {
                            for (int l = 0; l < Constant.CHESSBOARD_COL_SIZE.getNum(); l++) {
                                if(model.isValidMove(new ChessboardPoint(i,j),new ChessboardPoint(k,l))){
                                    count++;
                                }
                            }
                        }
                        if(count>0&&piece.getOwner()==PlayerColor.RED){
                            redLose=false;
                        }
                        if(count>0&&piece.getOwner()==PlayerColor.BLUE){
                            blueLose =false;
                        }
                    }
                }
            }
            if(redLose&&!blueLose){
                return 2;
            }else if(blueLose&&!redLose){
                return 1;
            }else return 0;
        }

    }
    public void restart() {
        model.restart();
        view.initiateChessComponent(model);
        view.repaint();
        this.currentPlayer = PlayerColor.BLUE;
        this.selectedPoint = null;
    }


    // click an empty cell
//    @Override
//    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
////        possibleMovePoints = null;
////        setCanStepFalse();
//        if (selectedPoint != null && model.isValidMove(selectedPoint, point) || model.getChessPieceAt(point) ==null) {//如果刚刚选有棋子且（空cell可以移动）或者（point是空的）
//            if (!model.isValidMove(selectedPoint, point)) {
//
//                component.revalidate();
//                component.repaint();
//                view.repaint();
//                view.revalidate();
//                JOptionPane.showMessageDialog(null, "Invalid Move!");
//            } else if (model.isValidMove(selectedPoint, point) && selectedPoint != null) {//如果是合法移动 else if (model.isValidMove(selectedPoint, point) && selectedPoint != null) {
//                if (model.isValidMove(selectedPoint, point) == false) {
//                    JOptionPane.showMessageDialog(null, "行棋步骤错误，错误编码:105", "Error", JOptionPane.ERROR_MESSAGE);
//                }
////                model.recordStep(selectedPoint, point, count, null);
//                count++;
//                model.moveChessPiece(selectedPoint, point);
////                possibleMovePoints = null;
//                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
//                selectedPoint = null;
//                swapColor();
//                view.repaint();
//                // TODO: if the chess enter Dens or Traps and so on
//                if (point.getName().equals("Trap")
//                        && ((this.currentPlayer.equals(PlayerColor.BLUE) && point.getRow() < 3)
//                        || (this.currentPlayer.equals(PlayerColor.RED) && point.getRow() > 6))) {
//                    this.model.getChessPieceAt(point).setRank(0);
//                }
//                if (point.getName().equals("Den")) {
//                    winner = currentPlayer;
////                    VictoryDialog a = new VictoryDialog();
////                    VictoryDialog.displayWinning(winner, a);
//                }// finish the game if the chess enter the Den
//            } // finish the game
//        }
////        if (this.currentPlayer.equals(PlayerColor.RED)) {
////            AIPlayIntegrated(getAiStatus());
////        }
//    }

    public void saveGame(String fileName) {
        String location = "save\\" + fileName + ".txt";
        File file = new File(location);

        try {
            if(file.exists()){     // 若文档存在，询问是否覆盖
                int n = JOptionPane.showConfirmDialog(view, "存档已存在，是否覆盖?", "", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    file.delete();
                }
            }

            // 创建文档
            FileWriter fileWriter = new FileWriter(location,true);

            fileWriter.write(model.steps.size() + "");
            fileWriter.write("\n");

            for (int i = 0; i < model.steps.size(); i++){
                fileWriter.write(model.steps.get(i).toString());
                fileWriter.write("\n");
            }

            fileWriter.write(currentPlayer == PlayerColor.BLUE ? "b" : "r");
            fileWriter.write("\n");

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessPiece chess = model.getGrid()[i][j].getPiece();
                    fileWriter.write(chessPieceToString(chess) + " ");
                }
                fileWriter.write("\n");
            }

            fileWriter.close();
            System.out.println("Save Done");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean loadGame(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("save"));
        chooser.showOpenDialog(view);
        File file = chooser.getSelectedFile();

        if (!file.getName().endsWith(".txt")){
            JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                    "101:文件后缀错误", JOptionPane.ERROR_MESSAGE);
            //System.out.println("检测到非法修改存档！重新开始游戏");
            //System.out.println("后缀错误");
            reset();
            return false;
        }

        try {
            String temp;
            ArrayList<String> readList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));

            while((temp = reader.readLine()) != null && !"".equals(temp)){
                readList.add(temp);
                //System.out.println(temp);
            }

            int num = Integer.parseInt(readList.remove(0));
//            System.out.println(num);
//            for (int i = 0; i < readList.size(); i++) {
//                System.out.println(readList.get(i));
//            }
            for (int i = 0; i <= num; i++) {
                String str = readList.get(i);

                if (i % 2 == 0 && str.charAt(0) != 'b'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                            "104:行棋方错误", JOptionPane.ERROR_MESSAGE);
                    reset();
                    return false;
                }
                if (i % 2 == 1 && str.charAt(0) != 'r'){
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                            "104:行棋方错误", JOptionPane.ERROR_MESSAGE);
                    reset();
                    return false;
                }
            }

            try {
                for (int i = num + 1; i < num + 10; i++) {
                    boolean b = true;
                    String[] chess= readList.get(i).split(" ");
                    if (chess.length != 7){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "102:棋盘错误，并非7*9", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    if (!checkName(chess)) b = false;
                    if (!b){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "103:棋子错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                        "102:棋盘错误，并非7*9", JOptionPane.ERROR_MESSAGE);
                reset();
                return false;
            }

            reset();
            for (int i = 0; i < num; i++) {
                String[] info = readList.get(i).split(" ");
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(info[1].charAt(1) + ""),
                        Integer.parseInt(info[1].charAt(3) + ""));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(info[2].charAt(1) + ""),
                        Integer.parseInt(info[2].charAt(3) + ""));
                boolean isCapture = !info[3].equals("null");

                if (!isCapture){
                    if (!model.isValidMove(src, dest)){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "105:行棋步骤错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    selectedPoint = null;
                    swapColor();
                    view.repaint();

                } else {
                    if (!model.isValidCapture(src, dest)){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "105:行棋步骤错误", JOptionPane.ERROR_MESSAGE);
                        reset();
                        return false;
                    }
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    swapColor();
                    view.repaint();
                    view.revalidate();
                }
            }

        } catch (Exception ex){
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                    "104:缺少行棋方", JOptionPane.ERROR_MESSAGE);
            reset();
        }
        return true;
    }
    private static boolean checkName(String[] chess){
        for (int i = 0; i < chess.length; i++) {
            if (!chess[i].equals("E") && !chess[i].equals("L") && !chess[i].equals("T") && !chess[i].equals("l")
                    && !chess[i].equals("w") && !chess[i].equals("d") && !chess[i].equals("c") && !chess[i].equals("r")
                    && !chess[i].equals("+")){
                return false;
            }
        }
        return true;
    }
    private static String chessPieceToString(ChessPiece chess){
        if (chess == null) return "+";
        else if (chess.getName().equals("Elephant")) return "E";
        else if (chess.getName().equals("Lion")) return "L";
        else if (chess.getName().equals("Tiger")) return "T";
        else if (chess.getName().equals("Leopard")) return "l";
        else if (chess.getName().equals("Wolf")) return "w";
        else if (chess.getName().equals("Dog")) return "d";
        else if (chess.getName().equals("Cat")) return "c";
        else if (chess.getName().equals("Rat")) return "r";
        else return "";
    }

    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            undoSelected();
            canStepPoints=null;

            view.setChessComponentAtGrid(point,view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            selectedPieceComponent=null;
            swapColor();
            view.repaint();
//            TODO:implement the winning function
            if(win()==2){
                System.out.println("red win");
                JOptionPane.showMessageDialog(view, " 红方胜利");
                reset();
            }else if(win()==1){
                System.out.println("blue win");
                JOptionPane.showMessageDialog(view, "蓝方胜利");
                reset();
            }
//
            if (AI==1){



                int[] ai= model.AI();
                ChessboardPoint src= new ChessboardPoint(ai[0],ai[1]);
                ChessboardPoint dest= new ChessboardPoint(ai[2],ai[3]);
                int judge= ai[4];
                if (judge==0){
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                } else {
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                }
//                canStepPoints = null;
//                setCanStepFalse();
                swapColor();
                view.repaint();
                view.getGridComponents()[dest.getRow()][dest.getCol()].revalidate();
                if(win()==1){
                    System.out.println("red win");
                    JOptionPane.showMessageDialog(view, " 红方胜利");
                    reset();
                }else if(win()==2){
                    System.out.println("blue win");
                    JOptionPane.showMessageDialog(view, "蓝方胜利");
                    reset();
                }
            } else if (AI==2) {
                easyAI();
            }

        }
    }

    // click a cell with a chess
//    @Override
//    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
//        if (selectedPoint == null) {
//            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
////                possibleMovePoints = getAndSetIsValidMovePoints(point);
////                possibleMovePoints = new ArrayList<>(getAndSetIsValidMovePoints(point));
//                selectedPoint = point;
////                model.findPossibleStep(point);
//                component.setSelected(true);
//                component.revalidate();
//                component.repaint();
//                view.repaint();
//                view.revalidate();
//            }
//        } else if (selectedPoint.equals(point)) {//click the same chess again and cancel selection
//            selectedPoint = null;
////            possibleMovePoints = null;
////            setCanStepFalse();
//            component.setSelected(false);
//            component.repaint();
//            view.repaint();
//            view.revalidate();
//        } else if (!(model.getChessPieceAt(point) == null)) {
//            if (model.isValidCapture(selectedPoint, point)) {
////                possibleMovePoints = null;
////                setCanStepFalse();
//                AnimalChessComponent chessComponent = (AnimalChessComponent) view.getGridComponentAt(point).getComponents()[0];
////                model.recordStep(selectedPoint, point, count, chessComponent);
//                count++;
//                model.captureChessPiece(selectedPoint, point);
////                new BGMofClick().PlayClickBGM("resource/Music/tear.wav");
//                view.removeChessComponentAtGrid(point);
//                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
//                selectedPoint = null;
//
//                swapColor();
//                view.repaint();
//                if (point.getName().equals("Trap") && ((this.currentPlayer.equals(PlayerColor.BLUE) && point.getRow() < 3)
//                        || (this.currentPlayer.equals(PlayerColor.RED) && point.getRow() > 6))) {
//                    this.model.getChessPieceAt(point).setRank(0);
//                }
//            }
//        } else {
//
//            JOptionPane.showMessageDialog(null, "行棋步骤错误，错误编码:105", "Error", JOptionPane.ERROR_MESSAGE);
//        }
////        if (this.currentPlayer.equals(PlayerColor.RED)) {
////            AIPlayIntegrated(getAiStatus());
////        }
//    }

    public void reset(){

        canStepPoints=null;
        model.initGrid();
        model.initPieces();
        view.removeChessComponent();
        view.initiateChessComponent(model);
        currentPlayer = PlayerColor.BLUE;
        selectedPoint = null;
//        setCanStepFalse();
        count=1;
        model.steps = new ArrayList<>();
        view.repaint();
        view.revalidate();
//        winner = null;

        model.redDead = new ArrayList<>();
        model.blueDead = new ArrayList<>();
//        timer.time = 45;
    }

    public ArrayList<ChessboardPoint> getCanStepPoints(ChessboardPoint src) {
        ArrayList<ChessboardPoint> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (model.isValidMove(src, dest)){
                    view.getGridComponents()[i][j].canStep = true;
                    list.add(dest);
                }
                if (model.isValidCapture(src, dest)){
                    view.getGridComponents()[i][j].canStep = true;
                    list.add(dest);
                }
            }
        }
        return list;
    }
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                canStepPoints=getCanStepPoints(point);
                selectedPoint = point;
                selectedPieceComponent= component;
                for(int i=0;i<9;i++){
                    for(int j=0;j<7;j++){
                        if(model.isValidMove(point,new ChessboardPoint(i,j))|| model.isValidCapture(point,new ChessboardPoint(i,j))){
//
//                            ImageIcon ima=new ImageIcon();
//                            ima=new ImageIcon("./images/BACKGROUND_PVC.png");
//                            ima.setImage(ima.getImage().getScaledInstance(1100,900,0));


                            System.out.print(i);
                            System.out.print(j);

                            view.getGridComponentAt(new ChessboardPoint(i,j)).setBackground(Color.PINK);
                            view.getGridComponentAt(new ChessboardPoint(i,j)).repaint();
                        }
                    }
                }
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            undoSelected();
            canStepPoints=null;
            selectedPoint = null;
//            selectedPieceComponent= null;
            component.setSelected(false);
            component.repaint();
        }else if(model.isValidCapture(selectedPoint,point)){
            undoSelected();

//            new SoundEffect().playEffect("./sounds/eat.wav");

            model.captureChessPiece(selectedPoint,point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
//            selectedPieceComponent=null;
            swapColor();
            view.revalidate();
            view.repaint();
            if(win()==1){
                System.out.println("red win");
                JOptionPane.showMessageDialog(view, " 红方胜利");
                reset();
            }else if(win()==2){
                System.out.println("blue win");
                JOptionPane.showMessageDialog(view, "蓝方胜利");
                reset();
            }

            if (AI==1){

                int[] ai= model.AI();
                ChessboardPoint src= new ChessboardPoint(ai[0],ai[1]);
                ChessboardPoint dest= new ChessboardPoint(ai[2],ai[3]);
                int judge= ai[4];
                if (judge==0){
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                } else {
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                }
//                canStepPoints = null;
//                setCanStepFalse();
                swapColor();
                view.repaint();
                view.getGridComponents()[dest.getRow()][dest.getCol()].revalidate();
                if(win()==2){
                    System.out.println("red win");
                    JOptionPane.showMessageDialog(view, " 红方胜利");
                    reset();
                }else if(win()==1){
                    System.out.println("blue win");
                    JOptionPane.showMessageDialog(view, "蓝方胜利");
                    reset();
                }
            } else if (AI==2) {
                easyAI();
            }
        }

    }

    public void easyAI() {
        System.out.println("easyAI");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (Exception e){
                    e.printStackTrace();
                }

                ChessboardPoint[] points = eastAIGetPoint();
                ChessboardPoint src = points[0];
                ChessboardPoint dest = points[1];

                if (model.getChessPieceAt(dest) == null){
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                } else {
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                }
                canStepPoints = null;
                setCanStepFalse();
                swapColor();
                view.repaint();
                view.getGridComponents()[dest.getRow()][dest.getCol()].revalidate();
                if(win()==2){
                    System.out.println("red win");
                    JOptionPane.showMessageDialog(view, " 红方胜利");
                    reset();
                }else if(win()==1){
                    System.out.println("blue win");
                    JOptionPane.showMessageDialog(view, "蓝方胜利");
                    reset();
                }
            }
        });
        thread.start();
    }
    public void setCanStepFalse() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                view.getGridComponents()[i][j].canStep = false;
            }
        }
    }
    public ChessboardPoint[] eastAIGetPoint(){
        ArrayList<ChessboardPoint> canMove = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == currentPlayer){
                    ArrayList<ChessboardPoint> list = getCanStepPoints(new ChessboardPoint(i, j));
                    if (list.size() != 0) canMove.add(new ChessboardPoint(i, j));
                }
            }
        }

        int size = canMove.size();
        Random random = new Random();
        int index = random.nextInt(size);
        ChessboardPoint src = canMove.get(index);

        ArrayList<ChessboardPoint> list = getCanStepPoints(src);
        size = list.size();
        index = random.nextInt(size);
        ChessboardPoint dest = list.get(index);

        return new ChessboardPoint[]{src, dest};
    }
    public void playback(){
        isPlayback = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Step> steps = model.steps;
                reset();
                for (int i = 0; i < steps.size(); i++) {
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Step step = steps.get(i);
                    ChessboardPoint src = step.src;
                    ChessboardPoint dest = step.dest;
                    boolean isCapture = step.captured != null;
                    if (!isCapture) {
                        model.moveChessPiece(src, dest);
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        selectedPoint = null;
                        swapColor();
                        view.repaint();
                    } else {
                        model.captureChessPiece(src, dest);
                        view.removeChessComponentAtGrid(dest);
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        swapColor();
                        view.repaint();
                        view.revalidate();
                    }
                }
            }
        });
        thread.start();

        isPlayback = false;
    }
    public void undoSelected(){
        for(int i=0;i<9;i++){
            for(int j=0;j<7;j++){
                if(view.getGridComponentAt(new ChessboardPoint(i,j)).getBackground().equals(Color.PINK)){
                    if(view.getRiverCell().contains(new ChessboardPoint(i,j))){
                        view.getGridComponentAt(new ChessboardPoint(i,j)).setBackground(new Color(0,128,255));

                    }else if(view.getDensCell().contains(new ChessboardPoint(i,j))){
                        view.getGridComponentAt(new ChessboardPoint(i,j)).setBackground(new Color(255, 255, 0));
                    }else if(view.getTrapCell().contains(new ChessboardPoint(i,j))){
                        view.getGridComponentAt(new ChessboardPoint(i,j)).setBackground( new Color (165, 42, 42, 255));
                    }else {
                        view.getGridComponentAt(new ChessboardPoint(i, j)).setBackground(new Color(204,102,0));
                    }
                    view.getGridComponentAt(new ChessboardPoint(i,j)).repaint();
//
                }
            }
        }
    }
    public void regretOneStep(){
        if(model.steps.size()==0)return;
        model.steps.remove(model.steps.size() - 1);
        ArrayList<Step> list = model.steps;
        reset();
        for (int i = 0; i < list.size(); i++) {
            Step step = list.get(i);
            ChessboardPoint src = step.src;
            ChessboardPoint dest = step.dest;
            boolean isCapture = step.captured != null;
            if (!isCapture){
                model.moveChessPiece(src, dest);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                selectedPoint = null;
                swapColor();
                view.repaint();
            } else {
                model.captureChessPiece(src, dest);
                view.removeChessComponentAtGrid(dest);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                swapColor();
                view.repaint();
                view.revalidate();
            }
        }
        for(int j=0;j<list.size();j++){
            System.out.println(list.get(j).toString());
        }
    }
}
