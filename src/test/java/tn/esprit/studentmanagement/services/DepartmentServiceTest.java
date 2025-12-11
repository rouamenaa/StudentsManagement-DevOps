package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    // 🔹 Test getAllDepartments()
    @Test
    void testGetAllDepartments() {
        Department d1 = new Department();
        d1.setId(1L);
        d1.setName("Informatique");

        Department d2 = new Department();
        d2.setId(2L);
        d2.setName("Finance");

        List<Department> mockDepartments = Arrays.asList(d1, d2);

        Mockito.when(departmentRepository.findAll()).thenReturn(mockDepartments);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getName());
    }

    // 🔹 Test getDepartmentById()
    @Test
    void testGetDepartmentById() {
        Department d = new Department();
        d.setId(1L);
        d.setName("Informatique");

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(d));

        Department result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    // 🔹 Test saveDepartment()
    @Test
    void testSaveDepartment() {
        Department d = new Department();
        d.setId(1L);
        d.setName("Informatique");

        Mockito.when(departmentRepository.save(d)).thenReturn(d);

        Department result = departmentService.saveDepartment(d);

        assertEquals("Informatique", result.getName());
    }

    // 🔹 Test deleteDepartment()
    @Test
    void testDeleteDepartment() {
        Long id = 1L;

        departmentService.deleteDepartment(id);

        Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(id);
    }
}
