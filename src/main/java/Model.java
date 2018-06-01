import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][]gameTiles;
    protected int score;
    protected int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
        score = 0;
        maxTile = 2;
    }

    private List<Tile> getEmptyTiles(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                if(gameTiles[i][j].isEmpty())
                    tiles.add(gameTiles[i][j]);
            }
        }
        return tiles;
    }

    private void addTile(){
        List<Tile> tiles = getEmptyTiles();
        if(tiles.size() == 0)return;
        Tile tile = tiles.get((int)((tiles.size()) * Math.random()));
        tile.value = (Math.random() < 0.9 ? 2 : 4);

    }

     protected void resetGameTiles(){
         this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
         for (int i = 0; i < gameTiles.length; i++){
             for (int j = 0; j < gameTiles[i].length; j++){
                 gameTiles[i][j] = new Tile();
             }
         }
        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles){
        boolean isChange = false;
            for (int i = 0; i < tiles.length; i++){
                if(tiles[i].isEmpty()){
                    for (int n = i + 1; n < tiles.length; n++){
                        if(!tiles[n].isEmpty()){
                            Tile tile = tiles[n];
                            tiles[n] = tiles[i];
                            tiles[i] = tile;
                            isChange = true;
                            break;
                        }
                    }
                }
            }

            return isChange;
    }
    private boolean mergeTiles(Tile[] tiles){
        boolean isChange = false;
        for (int i = 0; i < tiles.length - 1; i++){
            if(tiles[i].value == tiles[i + 1].value){
                tiles[i].value += tiles[i + 1].value;
                if(tiles[i].value != 0) isChange = true;
                tiles[i + 1].value = 0;
                compressTiles(tiles);
                if(tiles[i].value > maxTile)maxTile = tiles[i].value;
                score += tiles[i].value;
            }
        }
        return isChange;

    }

    protected void left(){
        if(isSaveNeeded){
            saveState(gameTiles);
        }

        boolean isChange = false;
        for (int i = 0; i < gameTiles.length; i++){
            if(compressTiles(gameTiles[i]) || mergeTiles(gameTiles[i])){
                isChange = true;
            }
        }
        if(isChange) addTile();
        isSaveNeeded = true;
    }

    private void turn(){
        Tile[][] tilesTmp = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = gameTiles.length - 1; i >= 0; i--){
            for (int j = 0; j < gameTiles[i].length; j++){
                tilesTmp[j][(gameTiles.length - 1) - i] = gameTiles[i][j];
            }
        }
        for (int i = 0; i < tilesTmp.length; i++){
            for (int j = 0; j < tilesTmp[i].length; j++){
                gameTiles[i][j] = tilesTmp[i][j];
            }
        }
    }



    protected void down(){
        saveState(gameTiles);
        boolean isChange = false;
        turn();

        for (int i = 0; i < gameTiles.length; i++){
            if(compressTiles(gameTiles[i]) || mergeTiles(gameTiles[i])){
                isChange = true;
            }
        }

        if(isChange) addTile();
        turn();
        turn();
        turn();
    }

    protected void up(){
        saveState(gameTiles);
        boolean isChange = false;
        turn();
        turn();
        turn();

        for (int i = 0; i < gameTiles.length; i++){
            if(compressTiles(gameTiles[i]) || mergeTiles(gameTiles[i])){
                isChange = true;
            }
        }

        if(isChange) addTile();
        turn();

    }

    protected void right(){
        saveState(gameTiles);
        boolean isChange = false;

        turn();
        turn();

        for (int i = 0; i < gameTiles.length; i++){
            if(compressTiles(gameTiles[i]) || mergeTiles(gameTiles[i])){
                isChange = true;
            }
        }

        if(isChange)addTile();
        turn();
        turn();
    }

    public boolean canMove(){

        if(getEmptyTiles().size() > 0)return true;

        for (int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                try {
                    if(gameTiles[i][j].value == gameTiles[i][j + 1].value)return true;
                } catch (Exception e) { }
                try {
                    if(gameTiles[i][j].value == gameTiles[i + 1][j].value)return true;
                } catch (Exception e) { }


            }
        }
        return false;
    }

    private void saveState(Tile[][] tiles){

        Tile[][] newTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0 ; i < newTiles.length; i++){
            for(int j = 0; j < newTiles[i].length; j++){
                newTiles[i][j] = new Tile(tiles[i][j].value);
            }
        }

        previousStates.push(newTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }
    public void rollback(){
        if(!previousStates.empty() && !previousScores.empty()){
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }

    }
    public void randomMove(){
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n){
            case 0 :
                left();
                break;
            case 1 :
                right();
                break;

            case 2 :
                up();
                break;

            case 3 :
                down();
                break;
        }
    }

    public boolean hasBoardChanged(){
        int x = 0;
        for (int i = 0 ; i < gameTiles.length; i++){
            for(int j = 0; j < gameTiles[i].length; j++){
                x += gameTiles[i][j].value;
            }
        }

        Tile[][] tiles = previousStates.peek();
        int y = 0;
        for (int i = 0 ; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                y += tiles[i][j].value;
            }
        }

        if(x != y)return true;
        return false;


    }

    public MoveEfficiency getMoveEfficiency(Move move){
        MoveEfficiency moveEfficiency;

        move.move();
        if(hasBoardChanged()){
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        }else {
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        }
        rollback();

        return moveEfficiency;

    }

    public void autoMove(){
        PriorityQueue<MoveEfficiency> moveEfficiencies = new PriorityQueue<>(4,Collections.reverseOrder());

        moveEfficiencies.add(getMoveEfficiency(() -> left()));
        moveEfficiencies.add(getMoveEfficiency(() -> right()));
        moveEfficiencies.add(getMoveEfficiency(() -> up()));
        moveEfficiencies.add(getMoveEfficiency(() -> down()));

        moveEfficiencies.peek().getMove().move();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }


}
