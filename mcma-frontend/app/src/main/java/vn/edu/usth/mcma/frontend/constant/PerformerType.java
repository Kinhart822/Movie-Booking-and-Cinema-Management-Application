package vn.edu.usth.mcma.frontend.constant;

import lombok.Getter;

@Getter
public enum PerformerType {
    Director(0),
    Actor(1),;
    private final int id;
    PerformerType(int id) {
        this.id = id;
    }
}
