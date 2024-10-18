import React, { useState, useEffect } from 'react';
import { getPizzas, createPizza, deletePizza, updatePizza, Pizza } from '../../services/PizzaService';
import { getToppings, Topping } from '../../services/ToppingService';
import { Button, TextField, Typography, Checkbox, FormControlLabel, List, ListItem, ListItemText, IconButton, Box, Alert } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const ChefDashboard: React.FC = () => {
    const [pizzas, setPizzas] = useState<Pizza[]>([]);
    const [availableToppings, setAvailableToppings] = useState<Topping[]>([]);
    const [newPizzaName, setNewPizzaName] = useState<string>('');
    const [selectedToppings, setSelectedToppings] = useState<number[]>([]);
    const [editingPizza, setEditingPizza] = useState<Pizza | null>(null);
    const [updatedPizzaName, setUpdatedPizzaName] = useState<string>('');
    const [updatedToppings, setUpdatedToppings] = useState<number[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchPizzas();
        fetchToppings();
    }, []);

    const fetchPizzas = async () => {
        const fetchedPizzas = await getPizzas();
        setPizzas(fetchedPizzas);
    };

    const fetchToppings = async () => {
        const fetchedToppings = await getToppings();
        setAvailableToppings(fetchedToppings);
    };

    const handleCreatePizza = async () => {
      if (!newPizzaName.trim()) {
        setError('Pizza name cannot be empty');
        return;
      }
      try {
          const newPizza = await createPizza(newPizzaName, selectedToppings);
          setPizzas([...pizzas, newPizza]);
          setNewPizzaName('');
          setSelectedToppings([]);
      } catch (err: any) {
          setError('Pizza Already Exists');
      }
  };

    const handleUpdatePizza = async (pizzaId: number) => {
      if (!updatedPizzaName.trim()) {
        setError('Pizza name cannot be empty');
        return;
      }
      try {
        const updatedPizza = await updatePizza(pizzaId, updatedPizzaName, updatedToppings);
        setPizzas(pizzas.map((pizza) => (pizza.id === pizzaId ? updatedPizza : pizza)));
        setEditingPizza(null);
        setUpdatedPizzaName('');
        setUpdatedToppings([]);
      } catch (err: any) {
        setError('Pizza Name Taken');
      }
    };

    const handleDeletePizza = async (pizzaId: number) => {
        await deletePizza(pizzaId);
        setPizzas(pizzas.filter((pizza) => pizza.id !== pizzaId));
    };

    const toggleToppingSelection = (toppingId: number) => {
        setSelectedToppings((prevSelected) =>
            prevSelected.includes(toppingId)
                ? prevSelected.filter((id) => id !== toppingId)
                : [...prevSelected, toppingId]
        );
    };

    return (
        <div>
            <Typography variant="h4"> Chef Dashboard </Typography>

            {error && <Alert severity="error" onClose={() => setError(null)}>{error}</Alert>}

            <Typography variant="h6">Create a new pizza</Typography>
            <TextField
                placeholder="Pizza name"
                value={newPizzaName}
                onChange={(e) => setNewPizzaName(e.target.value)}
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
            />
            <Typography variant="h6"> Select toppings: </Typography>
            <List>
                {availableToppings.map((topping) => (
                    <ListItem key={topping.id}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={selectedToppings.includes(topping.id)}
                                    onChange={() => toggleToppingSelection(topping.id)}
                                />
                            }
                            label={topping.name}
                        />
                    </ListItem>
                ))}
            </List>
            <Button variant="contained" color="primary" onClick={handleCreatePizza}> Create Pizza </Button>

            <Typography variant="h6" sx={{ mt: 3 }}> Pizzas </Typography>
            <List>
                {pizzas.map((pizza) => (
                    <ListItem key={pizza.id}>
                        <ListItemText primary={pizza.name} secondary={`Toppings: ${pizza.toppings.map(t => t.name).join(', ')}`} />
                        <Box>
                            <IconButton edge="end" onClick={() => {
                                setEditingPizza(pizza);
                                setUpdatedPizzaName(pizza.name);
                                setUpdatedToppings(pizza.toppings.map(t => t.id));
                            }}>
                                <EditIcon />
                            </IconButton>
                            <IconButton edge="end" onClick={() => handleDeletePizza(pizza.id)}>
                                <DeleteIcon />
                            </IconButton>
                        </Box>
                    </ListItem>
                ))}
            </List>

            {editingPizza && (
                <div>
                    <Typography variant="h6" sx={{ mt: 3 }}> Edit Pizza </Typography>
                    <TextField
                        placeholder="Pizza name"
                        value={updatedPizzaName}
                        onChange={(e) => setUpdatedPizzaName(e.target.value)}
                        variant="outlined"
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <Typography variant="h6"> Select new toppings: </Typography>
                    <List>
                        {availableToppings.map((topping) => (
                            <ListItem key={topping.id}>
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            checked={updatedToppings.includes(topping.id)}
                                            onChange={() => setUpdatedToppings((prev) =>
                                                prev.includes(topping.id)
                                                    ? prev.filter(id => id !== topping.id)
                                                    : [...prev, topping.id]
                                            )}
                                        />
                                    }
                                    label={topping.name}
                                />
                            </ListItem>
                        ))}
                    </List>
                    <Button variant="contained" color="secondary" onClick={() => handleUpdatePizza(editingPizza.id)}sx={{ mr: 1 }}> Update Pizza </Button>
                    <Button variant="outlined" onClick={() => setEditingPizza(null)}> Cancel </Button>
                </div>
            )}
        </div>
    );
};

export default ChefDashboard;
