package BlackJack.data;

public class Card {
    private String valores;
    private String formas;

    public Card(String valores, String formas) {
        this.valores = valores;
        this.formas = formas;
    }

    @Override
    public  String toString() {
        return valores + "-" + formas;
    }

    public int getValores() {
        if("AJQK".contains(valores)){
            if ("A".equals(valores)){
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(valores);
    }

    public boolean isAce(){
        return "A".equals(valores);
    }

    public String getImagePath(){
        return "/BlackJack/data/cards/" + toString() + ".png";
    }
}
