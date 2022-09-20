package edu.tingeso.lab1;
import edu.tingeso.lab1.repositories.EmpleadoRepository;
import edu.tingeso.lab1.services.EmpleadoService;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmpleadoTest {
    @Mock private EmpleadoRepository empleadoRepository;
    EmpleadoService empleadoService;

    @BeforeEach void setUp() {
        this.empleadoService = new EmpleadoService(empleadoRepository);
    }
    @Test
    void obtenerEmpleadosTest() {
        empleadoService.obtenerEmpleados();
        verify(empleadoRepository).findAll();
    }
}
