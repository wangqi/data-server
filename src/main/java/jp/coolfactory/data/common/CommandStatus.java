package jp.coolfactory.data.common;

/**
 * The command status.
 * If 'Continue', the next command in chain will take over.
 * If 'End', the request will be returned.
 * If 'Fail', the exception will be returned.
 *
 * Created by wangqi on 22/2/2017.
 */
public enum CommandStatus {

    Continue,
    End,
    Fail,

}
