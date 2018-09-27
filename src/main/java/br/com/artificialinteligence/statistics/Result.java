package br.com.artificialinteligence.statistics;

public final class Result {

    private final Integer finalFitness;

    private final Integer finalGeneration;

    private final String genes;

    Result(final Integer finalFitness,
           final Integer finalGeneration,
           final String genes) {
        this.finalFitness = finalFitness;
        this.finalGeneration = finalGeneration;
        this.genes = genes;
    }

    public Boolean isSuccess() {
        return Integer.valueOf(0).equals(finalFitness);
    }

    public Integer getFinalFitness() {
        return finalFitness;
    }

    public Integer getFinalGeneration() {
        return finalGeneration;
    }

    public String getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        return "Result{" +
                "finalFitness=" + finalFitness +
                ", finalGeneration=" + finalGeneration +
                ", genes='" + genes + '\'' +
                '}';
    }
}