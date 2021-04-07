package evolution;

public class Stats {


    public static int averageFitness(Bird[] birds)
    {
        double fitness = 0;
        for (Bird bird : birds) {
            fitness += bird.getFitness();
        }
        return (int)(fitness / birds.length);
    }

    public static int bestFitness(Bird[] birds){
        double fitness = 0;
        for (Bird bird : birds) {
            if (bird.getFitness() > fitness) {
                fitness = bird.getFitness();
            }
        }

        //System.out.println("True best Bird: "+ index);
        return (int)fitness;
    }
}
