package dao;

import models.UserTestModel;
import java.util.List;

public interface Mapper {
    List<UserTestModel> getUsers();
}