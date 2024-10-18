import api from './api';

export interface Topping {
    id: number;
    name: string;
}

export interface Pizza {
    id: number;
    name: string;
    toppings: Topping[];
}

export const getPizzas = async (): Promise<Pizza[]> => {
    const response = await api.get('/pizzas', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    });
    return response.data;
};

export const createPizza = async (name: string, toppings: number[]): Promise<Pizza> => {
    const response = await api.post(
        '/pizzas',
        { name, toppings: toppings.map((id) => ({ id })) },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
    return response.data;
};

export const updatePizza = async (id: number, name: string, toppings: number[]): Promise<Pizza> => {
    const response = await api.put(
        `/pizzas/${id}`,
        { name, toppings: toppings.map((id) => ({ id })) },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
    return response.data;
};

export const deletePizza = async (id: number): Promise<void> => {
    await api.delete(`/pizzas/${id}`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    });
};
