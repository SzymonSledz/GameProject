package components;

import engine.Component;

public class SpriteRenderer extends Component {

    public void start() {
        System.out.println("Starting");
    }

    @Override
    public void update(float dt) {
        System.out.println("Updating");
    }
}
