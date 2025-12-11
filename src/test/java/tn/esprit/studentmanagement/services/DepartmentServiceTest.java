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
        d1.setName("Informatique");
        d1.setLocation("Bureau 101");
        d1.setPhone("123456");
        d1.setHead("Dr. Benna");

        Department d2 = new Department();
        d2.setName("Finance");
        d2.setLocation("Bureau 102");
        d2.setPhone("654321");
        d2.setHead("Dr. Smith");

        List<Department> mockDepartments = Arrays.asList(d1, d2);
        Mockito.when(departmentRepository.findAll()).thenReturn(mockDepartments);

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getName());
        assertEquals("Finance", result.get(1).getName());
    }

    // 🔹 Test getDepartmentById()
    @Test
    void testGetDepartmentById() {
        Department d = new Department();
        d.setName("Informatique");
        d.setLocation("Bureau 101");
        d.setPhone("123456");
        d.setHead("Dr. Benna");

        Mockito.when(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(d));

        Department result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    // 🔹 Test saveDepartment()
    @Test
    void testSaveDepartment() {
        Department d = new Department();
        d.setName("Informatique");
        d.setLocation("Bureau 101");
        d.setPhone("123456");
        d.setHead("Dr. Benna");

        Mockito.when(departmentRepository.save(d)).thenReturn(d);

        Department result = departmentService.saveDepartment(d);

        assertEquals("Informatique", result.getName());
        assertEquals("Bureau 101", result.getLocation());
    }

    // 🔹 Test deleteDepartment()
    @Test
    void testDeleteDepartment() {
        Long id = 1L;

        departmentService.deleteDepartment(id);

        Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(id);
    }
}
