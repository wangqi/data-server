package jp.coolfactory.data.common;

/**
 *
 * Created by wangqi on 22/2/2017.
 */
public interface Handler<T> {

    CommandStatus handle(T t);

}
