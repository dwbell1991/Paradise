package pkg.paradise.entity.mob;

import pkg.paradise.entity.Entity;
import pkg.paradise.graphics.Screen;
import pkg.paradise.graphics.Sprite;

public abstract class Mob extends Entity {

    protected Sprite sprite;
    protected int dir = 0;
    protected boolean moving = false;

    //Animation
    protected double aTimer;
    protected int currAnim;

    /****************************************************
     * Name:        move
     * Description: This method controls mob direction,
     * and also checks for collision detection of a given 
     * mob.
     ****************************************************/
    public void move(int xMove, int yMove) {
        //Right
        if (xMove > 0) {
            dir = 1;
        }
        //Left
        if (xMove < 0) {
            dir = 2;
        }
        //Down
        if (yMove > 0) {
            dir = 0;
        }
        //Up
        if (yMove < 0) {
            dir = 3;
        }

        //Collision detection for movement
        if (!collision(0, yMove)) {
            y += yMove;
        }

        if (!collision(xMove, 0)) {
            x += xMove;
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Screen screen) {

    }

    /****************************************************
     * Name:        animate
     * Description: Controls animation for a given 2D array
     * of sprites which is 3x3 matrix. 
     * delta: Time per frames,
     * timer: Time to wait before proceeding to next sprite image, 
     * selection: Which player character was chosen 
     * dir = 0:Down, 1:Right, 2:Left, 3:Up
     ****************************************************/
    public void animate(float delta, double timer, int selection) {
        aTimer += delta;
        if (moving) {
            if (aTimer > timer) {
                sprite = Sprite.ALL_CHARACTERS.get(selection)[dir][currAnim];
                if (currAnim == 3) {
                    currAnim = 0;
                } else {
                    currAnim++;
                }
                aTimer = 0;
            }
        } else {
            sprite = Sprite.ALL_CHARACTERS.get(selection)[dir][0];
            currAnim = 0;
        }
    }

    private boolean collision(int xMove, int yMove) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            //+ c % 2 *(collision width) - (pixel shift)
            int xt = ((x + xMove) + c % 2 * 9 - 13) / 16;
            int yt = ((y + yMove) + c / 2 * 5 - 8) / 16;
            if (level.getTile(xt, yt).solid()) {
                solid = true;
            }
        }

        return solid;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDir() {
        return this.dir;
    }

    public void setMoving(int moving) {
        this.moving = moving == 1;
    }

    public String getMoving() {
        String ret = "0";
        if (moving) {
            ret = "1";
        }
        return ret;
    }
}
