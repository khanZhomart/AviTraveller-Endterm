package models;

public class Card {
    private String card_num, card_type;
    private int exp_month, exp_year, card_cvv;

    private Card(CardBuilder builder) {
        this.card_num = builder.card_num;
        this.card_cvv = builder.card_cvv;
        this.card_type = builder.card_type;
        this.exp_month = builder.exp_month;
        this.exp_year = builder.exp_year;
    }

    public String getCard_num() {
        return card_num;
    }

    public String getCard_type() {
        return card_type;
    }

    public int getCard_cvv() {
        return card_cvv;
    }

    public int getExp_month() {
        return exp_month;
    }

    public int getExp_year() {
        return exp_year;
    }

    @Override
    public String toString() {
        return getCard_type() + "\nCARD NUM: ..." + getCard_num().substring(getCard_num().length() - 4) +
                "\nEXP. DATE: " + getExp_month() + "/" + getExp_year();
    }

    public static class CardBuilder {
        private String card_num, card_type;
        private int exp_month, exp_year, card_cvv;

        public CardBuilder() {

        }

        public CardBuilder setCardNum(String card_num) {
            this.card_num = card_num;
            return this;
        }

        public CardBuilder setCardCVV(int card_cvv) {
            this.card_cvv = card_cvv;
            return this;
        }

        public CardBuilder setCardType(String type) {
            this.card_type = type;
            return this;
        }

        public CardBuilder setExpDate(int exp_month, int exp_year) {
            this.exp_month = exp_month;
            this.exp_year = exp_year;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
