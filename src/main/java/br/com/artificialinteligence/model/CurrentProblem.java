package br.com.artificialinteligence.model;

// TODO ESSE OBJETO NÃO DEVE EXISTIR REFATORAR DEPOIS
public final class CurrentProblem {

    private static CurrentProblem INSTANCE = new CurrentProblem();

//    private final String objective = "GREMIO > INTERNACIONAL";

    private final String objective = "100101011010010110010000000110101010100101101010111001";

//    private CurrentProblem(final String objective) {
//        this.objective = objective;
//    }

    public static CurrentProblem getInstance() {
        return INSTANCE;
    }

    public String getObjective() {
        return objective;
    }
}