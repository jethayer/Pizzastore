import api from './api'; // Adjust path as needed

export interface Topping {
    id: number;
    name: string;
}

export const getToppings = async (): Promise<Topping[]> => {
    const response = await api.get('/toppings', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    });
    return response.data;
};

export const createTopping = async (name: string): Promise<Topping> => {
    const response = await api.post(
        '/toppings',
        { name },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
    return response.data;
};

export const updateTopping = async (id: number, name: string): Promise<Topping> => {
    const response = await api.put(
        `/toppings/${id}`,
        { name },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        }
    );
    return response.data;
};

export const deleteTopping = async (id: number): Promise<void> => {
    await api.delete(`/toppings/${id}`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    });
};
