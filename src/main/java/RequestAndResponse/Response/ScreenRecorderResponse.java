package RequestAndResponse.Response;

import Model.Board.Board;
import Model.Player.Player;

import java.util.ArrayList;

public class ScreenRecorderResponse extends Response {



    ArrayList<Board> boards;


    public ScreenRecorderResponse(ArrayList<Board> boards) {
        this.boards=boards;
    }


    @Override
    public void execute() {

    }




    //getter and setters
    //*********************

    public ArrayList<Board> getBoard() {
        return boards;
    }

    public void setBoard(ArrayList<Board> boards) {
        this.boards = boards;
    }



}
