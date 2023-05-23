public enum Category {
    FOOD, OTHERS, GAMES, BUSINESS, COMMUNICATION, ENTERTAINMENT, HEALTH, EVENTS, BOOK, ALL;

    Category() {

    }
//    public static Category[] values() {
//
//    }
    static Category stringToCategory(String category) {
        if(category.equals("FOOD"))
            return FOOD;
        if(category.equals("GAMES"))
            return GAMES;
        if(category.equals("BUSINESS"))
            return BUSINESS;
        if(category.equals("COMMUNICATION"))
            return COMMUNICATION;
        if(category.equals("ENTERTAINMENT"))
            return ENTERTAINMENT;
        if(category.equals("HEALTH"))
            return HEALTH;
        if(category.equals("EVENTS"))
            return EVENTS;
        if(category.equals("BOOK"))
            return BOOK;
        if(category.equals("*"))
            return ALL;
        return OTHERS;
    }
//    static Category valueOf(String ) {
//
//    }

}
