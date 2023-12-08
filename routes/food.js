const express = require('express');
const router = express.Router();
const foodController = require('../controllers/foodController');
const authMiddleware = require('../middlewares/authMiddleware');

// Middleware untuk memeriksa apakah pengguna telah login
router.use(authMiddleware.checkAuth);

// Route untuk mendapatkan semua makanan
router.get('/', foodController.getAllFoods);

// Route untuk mendapatkan detail makanan
router.get('/:foodId', foodController.getFoodById);

// Route untuk menambahkan makanan baru
router.post('/', foodController.addFood);

// Route untuk mengupdate makanan
router.put('/:foodId', foodController.updateFood);

// Route untuk menghapus makanan
router.delete('/:foodId', foodController.deleteFood);

module.exports = router;
