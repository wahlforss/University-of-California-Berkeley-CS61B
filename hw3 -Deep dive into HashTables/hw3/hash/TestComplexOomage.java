package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();

        LinkedList<ComplexOomage>[] buckets = new LinkedList[10];
        for (int i = 0; i < 10; i++){
            buckets[i] = new LinkedList();
        }
        for (ComplexOomage x : oomages) {
            int hash = (x.hashCode() & 0x7FFFFFFF) % 10;
            buckets[hash].add(x);
        }
        for (LinkedList l : buckets) {
            if (N / 50 > l.size()) return false;
            if (N / 2.5 < l.size()) return false;
        }

        return true;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        for (int a = 0; a < 10; a += 1) {
            int N = StdRandom.uniform(5, 10);
            ArrayList<Integer> params = new ArrayList<Integer>(N);
            for (int i = 0; i < N; i += 1) {
                params.add(255);
            }
            ComplexOomage test = new ComplexOomage(params);
            System.out.println(test.hashCode());
            oomages.add(new ComplexOomage(params));
        }
        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
