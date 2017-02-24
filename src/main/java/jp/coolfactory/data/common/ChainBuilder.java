package jp.coolfactory.data.common;

/**
 * It's the chain-of-responsibility design pattern from here: http://stackoverflow.com/questions/36484459/chain-of-responsibility-lambda
 *
 * Created by wangqi on 22/2/2017.
 */
public class ChainBuilder<T> {

    public static <T> ChainBuilder<T> chainBuilder() {
        return new ChainBuilder<>();
    }

    private HandlerImpl<T> first;

    private ChainBuilder() {
    }

    public SuccessorBuilder first(Handler<T> handler) {
        first = new HandlerImpl<>(handler);
        return new SuccessorBuilder(first);
    }

    public class SuccessorBuilder {
        private HandlerImpl<T> current;

        private SuccessorBuilder(HandlerImpl<T> current) {
            this.current = current;
        }

        public SuccessorBuilder successor(Handler<T> successor) {
            current.setSuccessor(successor);
            return this;
        }

        public Chain<T> build() {
            return first;
        }
    }

    private static class HandlerImpl<T> implements Chain<T> {
        private final Handler<T> delegate;
        private Handler<T> successor;

        public HandlerImpl(Handler<T> delegate) {
            this.delegate = delegate;
        }

        private void setSuccessor(Handler<T> successor) {
            this.successor = successor;
        }

        @Override
        public void handle(T t) {
            CommandStatus status = delegate.handle(t);
            if ( status == CommandStatus.Continue) {
                if ( successor != null ) {
                    successor.handle(t);
                }
            } else if ( status == CommandStatus.End ) {
                return;
            } else if ( status == CommandStatus.Fail ) {
                return;
            }
        }
    }
}
