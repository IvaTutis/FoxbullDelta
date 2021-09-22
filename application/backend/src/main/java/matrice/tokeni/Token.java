package matrice.tokeni;

import java.util.Objects;

public class Token {

    private Object value;
    private TokenType type;

    public Token(){
    }

    public Token(TokenType type, Object value){
        this.type = type;
        this.value = value;
    }
    public Object getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" + type + ", " + value +"]";
    }


}
