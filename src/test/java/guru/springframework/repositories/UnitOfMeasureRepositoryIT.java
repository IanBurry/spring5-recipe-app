package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescriptionTsp() {
        String testedUoM = "Teaspoon";
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByDescription(testedUoM).get();
        assertEquals(testedUoM, unitOfMeasure.getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        String testedUoM = "Cup";
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByDescription(testedUoM).get();
        assertEquals(testedUoM, unitOfMeasure.getDescription());
    }
}