package de.dafri.dwb.data;

import de.dafri.dwb.data.model.CategoryModel;
import de.dafri.dwb.data.model.CategoryRelationModel;
import de.dafri.dwb.domain.Category;

import java.util.*;
import java.util.stream.Collectors;

public class CategoryTreeCreator {

    public List<Category> createTree(List<CategoryModel> categoryModels, List<CategoryRelationModel> relationModels) {
        Map<Long, CategoryModel> categoryIdlMap = categoryModels.stream()
                .collect(Collectors.toMap(CategoryModel::id, categoryModel -> categoryModel));

        Map<Long, List<CategoryRelationModel>> relationsTree = new HashMap<>();
        for (CategoryRelationModel relationModel : relationModels) {
            if (!relationsTree.containsKey(relationModel.parentId())) {
                relationsTree.put(relationModel.parentId(), new ArrayList<>());
            }
            relationsTree.get(relationModel.parentId()).add(relationModel);
        }

        // sort
        Map<Long, List<CategoryRelationModel>> sortedRelationsTree = new HashMap<>();
        for (Map.Entry<Long, List<CategoryRelationModel>> entry : relationsTree.entrySet()) {
            List<CategoryRelationModel> list = entry.getValue().stream().sorted(Comparator.comparing(CategoryRelationModel::sort)).toList();
            sortedRelationsTree.put(entry.getKey(), list);
        }

        // find root elements
        List<CategoryModel> rootElements = categoryModels.stream()
                .filter(c -> c.nr().equals("0") || c.nr().startsWith("00"))
                .toList();

        List<Category> categoryTree = new ArrayList<>();
        for (CategoryModel rootElement : rootElements) {
            List<CategoryRelationModel> childRelations = sortedRelationsTree.getOrDefault(rootElement.id(), new ArrayList<>());

            List<Category> children = new ArrayList<>();
            for (CategoryRelationModel childRelation : childRelations) {
                CategoryModel child = categoryIdlMap.get(childRelation.childId());
                if (child != null) {
                    children.add(new Category(child.id(), child.nr(), child.name(), child.description(), List.of()));
                }
            }

            categoryTree.add(new Category(rootElement.id(), rootElement.nr(), rootElement.name(), rootElement.description(), children));
        }

        return categoryTree;
    }

}
