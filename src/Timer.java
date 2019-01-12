public class Timer
{
    long start;
    long end;
    
    long taken;
    
    public void start() {
        start = System.nanoTime();
    }
    
    public void stop() {
        end = System.nanoTime();
        taken = end - start;
    }
    
    public long taken() { return taken; }
    
    public double takenInS() {
        double t = (double) taken;
        
        t /= 1000000000;
        
        return t;
    }
}