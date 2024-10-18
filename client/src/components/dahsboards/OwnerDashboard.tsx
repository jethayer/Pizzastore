import React, { useEffect, useState } from 'react';
import { getToppings, createTopping, updateTopping, deleteTopping } from '../../services/ToppingService';
import { Button, TextField, Typography, List, ListItem, ListItemText, IconButton, Box, Alert } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

interface Topping {
    id: number;
    name: string;
}

const OwnerDashboard: React.FC = () => {
    const [toppings, setToppings] = useState<Topping[]>([]);
    const [newTopping, setNewTopping] = useState<string>('');
    const [editTopping, setEditTopping] = useState<{ id: number; name: string } | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchToppings();
    }, []);

    const fetchToppings = async () => {
        const fetchedToppings = await getToppings();
        setToppings(fetchedToppings);
    };

    const handleCreateTopping = async () => {
        if (!newTopping.trim()) {
            setError('Topping name cannot be empty');
            return;
        }
        try {
            await createTopping(newTopping);
            setNewTopping('');
            fetchToppings();
        } catch (err: any) {
            setError('Topping Already Exists');
        }
    };

    const handleUpdateTopping = async () => {
        if (!editTopping?.name.trim()) {
            setError('Topping name cannot be empty');
            return;
        }
        try {
            if (editTopping) {
                await updateTopping(editTopping.id, editTopping.name);
                setEditTopping(null);
                fetchToppings();
            }
        } catch {
            setError('Topping Name Taken');
        }
    };

    const handleDeleteTopping = async (id: number) => {
        try {
            await deleteTopping(id);
            fetchToppings();
        } catch (err: any) {
            setError('Topping used in a Pizza. Delete or update pizzas that use this topping to delete it.')
        }
    };

    return (
        <div>
            <Typography variant="h4" gutterBottom> Owner Dashboard - Manage Toppings </Typography>

            {error && <Alert severity="error" onClose={() => setError(null)}>{error}</Alert>}

            <Typography variant="h6"> Create Topping </Typography>
            <TextField
                label="Topping name"
                value={newTopping}
                onChange={(e) => setNewTopping(e.target.value)}
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
            />
            <Button variant="contained" color="primary" onClick={handleCreateTopping}> Add Topping </Button>

            {editTopping && (
                <div>
                    <Typography variant="h6" sx={{ mt: 3 }}> Edit Topping </Typography>
                    <TextField
                        label="Topping name"
                        value={editTopping.name}
                        onChange={(e) => setEditTopping({ ...editTopping, name: e.target.value })}
                        variant="outlined"
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <Button variant="contained" color="secondary" onClick={handleUpdateTopping}sx={{ mr: 1 }}> Update Topping </Button>
                    <Button variant="outlined" onClick={() => setEditTopping(null)}>Cancel</Button>
                </div>
            )}

            <Typography variant="h6" sx={{ mt: 3 }}> Toppings </Typography>
            <List>
                {toppings.map(topping => (
                    <ListItem key={topping.id}>
                        <ListItemText primary={topping.name} />
                        <Box>
                            <IconButton edge="end" onClick={() => setEditTopping(topping)}>
                                <EditIcon />
                            </IconButton>
                            <IconButton edge="end" onClick={() => handleDeleteTopping(topping.id)}>
                                <DeleteIcon />
                            </IconButton>
                        </Box>
                    </ListItem>
                ))}
            </List>

        </div>
    );
};

export default OwnerDashboard;
