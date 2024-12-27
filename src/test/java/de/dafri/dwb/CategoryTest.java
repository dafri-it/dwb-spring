package de.dafri.dwb;

import de.dafri.dwb.data.CategoryDto;
import de.dafri.dwb.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryTest {

    @Autowired
    CategoryDto categoryDto;

    @Test
    public void have_six_main_categories() {
        List<Category> tree = categoryDto.getCategoryTree();

        Assertions.assertEquals(6, tree.size());
    }

    @Test
    public void first_category_should_have_no_children() {
        List<Category> tree = categoryDto.getCategoryTree();
        Assertions.assertEquals(0, tree.getFirst().children().size());
    }

    @Test
    public void second_category_should_be_recht() {
        List<Category> tree = categoryDto.getCategoryTree();
        Category second = tree.get(1);
        Assertions.assertEquals("Recht", second.name());
    }

    @Test
    public void recht_should_have_children_as_described() {
        List<Category> tree = categoryDto.getCategoryTree();
        Category recht = tree.get(1);

        Assertions.assertEquals("210", recht.children().get(0).nr());
        Assertions.assertEquals("220", recht.children().get(1).nr());
        Assertions.assertEquals("230", recht.children().get(2).nr());
    }

}
