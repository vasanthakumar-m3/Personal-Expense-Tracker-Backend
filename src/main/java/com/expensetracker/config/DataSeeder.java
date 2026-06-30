package com.expensetracker.config;

import com.expensetracker.entity.Category;
import com.expensetracker.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Salary", "INCOME"));
            categoryRepository.save(new Category(null, "Freelance", "INCOME"));
            categoryRepository.save(new Category(null, "Investment", "INCOME"));
            categoryRepository.save(new Category(null, "Food", "EXPENSE"));
            categoryRepository.save(new Category(null, "Transport", "EXPENSE"));
            categoryRepository.save(new Category(null, "Shopping", "EXPENSE"));
            categoryRepository.save(new Category(null, "Entertainment", "EXPENSE"));
            categoryRepository.save(new Category(null, "Utilities", "EXPENSE"));
            categoryRepository.save(new Category(null, "Health", "EXPENSE"));
        }
    }
}
