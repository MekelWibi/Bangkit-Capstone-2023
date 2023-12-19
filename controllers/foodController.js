const  Food  = require('../models/Food');

const getAllFoods = async (req, res) => {
  try {
    // Ambil semua data makanan dari database
    const foods = await Food.findAll();

    res.status(200).json(foods);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal mendapatkan data makanan' });
  }
};

const getFoodById = async (req, res) => {
  try {
    const foodId = req.params.foodId;

    // Cari makanan berdasarkan ID
    const food = await Food.findByPk(foodId);

    if (!food) {
      return res.status(404).json({ message: 'Makanan tidak ditemukan' });
    }

    res.status(200).json(food);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal mendapatkan data makanan' });
  }
};

const addFood = async (req, res) => {
  try {
    const { name, calories } = req.body;

    // Tambahkan makanan baru ke database
    const newFood = await Food.create({ name, calories });

    res.status(201).json({ message: 'Makanan berhasil ditambahkan', food: newFood });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal menambahkan makanan' });
  }
};

const updateFood = async (req, res) => {
  try {
    const foodId = req.params.foodId;
    const { name, calories } = req.body;

    // Perbarui data makanan di database
    const updatedFood = await Food.update({ name, calories }, { where: { id: foodId } });

    if (updatedFood[0] === 0) {
      return res.status(404).json({ message: 'Makanan tidak ditemukan' });
    }

    res.status(200).json({ message: 'Makanan berhasil diperbarui' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal memperbarui makanan' });
  }
};

const deleteFood = async (req, res) => {
  try {
    const foodId = req.params.foodId;

    // Hapus data makanan dari database
    const deletedFood = await Food.destroy({ where: { id: foodId } });

    if (!deletedFood) {
      return res.status(404).json({ message: 'Makanan tidak ditemukan' });
    }

    res.status(200).json({ message: 'Makanan berhasil dihapus' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal menghapus makanan' });
  }
};

module.exports = {
  getAllFoods,
  getFoodById,
  addFood,
  updateFood,
  deleteFood,
};
