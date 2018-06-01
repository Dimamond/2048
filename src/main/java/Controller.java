import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private View view;
    private Model model;
    private static int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    public int getScore(){
        return model.score;
    }

    public void resetGame(){
        model.score = 0;
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE)resetGame();
        if(!model.canMove())view.isGameLost = true;

        if(!view.isGameLost && !view.isGameWon){
            switch (key){
                case KeyEvent.VK_LEFT :
                    model.left();
                    break;
                case KeyEvent.VK_RIGHT :
                    model.right();
                    break;

                case KeyEvent.VK_UP :
                    model.up();
                    break;
                case KeyEvent.VK_Z :
                    model.rollback();
                    break;
                case KeyEvent.VK_DOWN :
                    model.down();
                    break;
                case KeyEvent.VK_R :
                    model.randomMove();
                    break;
                case KeyEvent.VK_A :
                    model.autoMove();
                    break;

            }
        }

        if(model.maxTile == WINNING_TILE){
            view.isGameWon = true;
        }
        view.repaint();

    }

    public View getView() {
        return view;
    }
}
