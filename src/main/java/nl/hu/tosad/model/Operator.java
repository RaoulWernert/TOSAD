package nl.hu.tosad.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class Operator {
    public enum Type {
        Equal,
        NotEqual,
        Greater,
        Less,
        GreaterOrEqual,
        LessOrEqual,
        In,
        NotIn
    }
    private Type type;
    public Operator(Type t){
        type = t;
    }
    public Type getType(){
        return type;
    }
}
