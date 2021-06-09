package org.game.input.controller.ia;

import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.utils.Constants;

public class IAController {
    private Bar bar;
    private Ball ball;

    public IAController(Bar bar, Ball ball) {
        this.bar = bar;
        this.ball = ball;
    }

    public void update(Double delta) {
        if (ball.getPosition().getY() <= bar.getPosition().getY())
            bar.up(delta);
        if (ball.getPosition().getY() + Constants.BALL_HEIGHT >= bar.getPosition().getY() + Constants.BAR_HEIGHT)
            bar.down(delta);
    }
}