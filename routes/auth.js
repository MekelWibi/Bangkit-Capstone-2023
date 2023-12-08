const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');

// Route untuk registrasi pengguna
router.post('/register', authController.register);

// Route untuk login pengguna
router.post('/login', authController.login);

// Route untuk logout pengguna
router.delete('/logout', authController.logout);

module.exports = router;
