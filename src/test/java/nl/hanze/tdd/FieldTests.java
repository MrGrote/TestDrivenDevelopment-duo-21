package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class FieldTests{
    @Test
    void whenGetNeighboursThenLengthSix(){
        Field field = new Field();
        int q = 0;
        int r = 0;
        int[][] neigbours = field.getNeigbours(q, r);
        assertEquals(6, neigbours.length);
    }
}