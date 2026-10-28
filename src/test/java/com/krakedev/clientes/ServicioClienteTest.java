package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.services.ServicioCliente;

class ServicioClienteTest {

    private ServicioCliente servicioCliente;
    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada test para asegurar un estado limpio
        servicioCliente = new ServicioCliente();
        
        // Asumiendo que tu entidad Cliente tiene un constructor con (cedula, nombre, apellido)
        cliente1 = new Cliente("1700000001", "Holger", "Ordoñez", "ha31@gmail.com");
        cliente2 = new Cliente("1700000002", "Juan", "Perez", "ce@gmail.com");
    }

    @Test
    void testCrearClienteExitoso() {
        Cliente resultado = servicioCliente.crear(cliente1);
        
        assertNotNull(resultado, "El cliente no debe ser null tras crearlo");
        assertEquals("1700000001", resultado.getCedula());
        assertEquals(1, servicioCliente.listar().size(), "La lista debe tener 1 cliente");
    }

    @Test
    void testCrearClienteDuplicado() {
        servicioCliente.crear(cliente1);
        Cliente resultadoDuplicado = servicioCliente.crear(cliente1); // Intento de duplicado
        
        assertNull(resultadoDuplicado, "Debe retornar null si la cédula ya existe");
        assertEquals(1, servicioCliente.listar().size(), "La lista no debe aumentar su tamaño");
    }

    @Test
    void testBuscarPorCedulaExistente() {
        servicioCliente.crear(cliente1);
        Cliente encontrado = servicioCliente.buscarPorCedula("1700000001");
        
        assertNotNull(encontrado);
        assertEquals("Holger", encontrado.getNombre());
    }

    @Test
    void testBuscarPorCedulaNoExistente() {
        Cliente encontrado = servicioCliente.buscarPorCedula("9999999999");
        assertNull(encontrado, "Debe retornar null si el cliente no existe");
    }

    @Test
    void testListarClientes() {
        servicioCliente.crear(cliente1);
        servicioCliente.crear(cliente2);
        
        List<Cliente> lista = servicioCliente.listar();
        
        assertEquals(2, lista.size(), "La lista debe contener 2 clientes");
    }

    @Test
    void testActualizarClienteExistente() {
        servicioCliente.crear(cliente1);
        
        Cliente clienteNuevosDatos = new Cliente("1700000001", "Adrian", "Espinosa", "ha33@gmail.com");
        Cliente actualizado = servicioCliente.actualizar("1700000001", clienteNuevosDatos);
        
        assertNotNull(actualizado);
        assertEquals("Adrian", actualizado.getNombre());
        assertEquals("Espinosa", actualizado.getApellido());
        assertEquals("ha33@gmail.com", actualizado.getEmail());
    }

    @Test
    void testActualizarClienteNoExistente() {
        Cliente clienteNuevosDatos = new Cliente("9999999999", "Adrian", "Espinosa", "ce@gmail.com");
        Cliente actualizado = servicioCliente.actualizar("9999999999", clienteNuevosDatos);
        
        assertNull(actualizado, "Debe retornar null al intentar actualizar un cliente que no existe");
    }

    @Test
    void testEliminarClienteExistente() {
        servicioCliente.crear(cliente1);
        boolean eliminado = servicioCliente.eliminar("1700000001");
        
        assertTrue(eliminado, "Debe retornar true si el cliente fue eliminado");
        assertEquals(0, servicioCliente.listar().size(), "La lista debe quedar vacía");
    }

    @Test
    void testEliminarClienteNoExistente() {
        boolean eliminado = servicioCliente.eliminar("9999999999");
        
        assertFalse(eliminado, "Debe retornar false si el cliente no existía");
    }
}