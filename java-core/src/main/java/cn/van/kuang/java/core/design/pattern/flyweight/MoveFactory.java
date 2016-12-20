package cn.van.kuang.java.core.design.pattern.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MoveFactory {

    private static Map<Move.Type, Move> moves = new ConcurrentHashMap<>();

    public static Move create(Move.Type type) {
        if (moves.containsKey(type)) {
            return moves.get(type);
        }

        Move move;
        switch (type) {
            case SPIN:
                move = new Spin();
                break;
            case CROSSOVER:
                move = new Crossover();
                break;
            case PULLUP:
                move = new PullUp();
                break;
            case HOOKSHOT:
                move = new HookShot();
                break;
            default:
                throw new IllegalArgumentException("No such move");
        }

        moves.put(type, move);

        return move;
    }

}
