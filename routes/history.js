const express = require('express');
const router = express.Router();
const historyController = require('../controllers/historyController');
const authMiddleware = require('../middlewares/authMiddleware');

// Middleware untuk memeriksa apakah pengguna telah login
router.use(authMiddleware.checkAuth);

// Route untuk mendapatkan riwayat pengguna
router.get('/', historyController.getHistoryByUserId);

// Route untuk menambahkan entri riwayat baru
router.post('/', historyController.addHistoryEntry);

module.exports = router;
