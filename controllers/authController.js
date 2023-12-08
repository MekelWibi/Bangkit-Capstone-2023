const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { User } = require('../models/User');

const register = async (req, res) => {
  try {
    const { username, password } = req.body;

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Simpan pengguna baru ke database
    const newUser = await User.create({
      username,
      password: hashedPassword,
    });

    res.status(201).json({ message: 'Registrasi berhasil', user: newUser });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Registrasi gagal' });
  }
};

const login = async (req, res) => {
  try {
    const { username, password } = req.body;

    // Cari pengguna berdasarkan username
    const user = await User.findOne({ where: { username } });

    if (!user) {
      return res.status(404).json({ message: 'Pengguna tidak ditemukan' });
    }

    // Bandingkan password yang diinput dengan password di database
    const isPasswordValid = await bcrypt.compare(password, user.password);

    if (!isPasswordValid) {
      return res.status(401).json({ message: 'Password salah' });
    }

    // Buat token JWT untuk pengguna
    const token = jwt.sign({ userId: user.id }, 'secret_key', { expiresIn: '1h' });

    // Set cookie dengan token
    res.cookie('token', token, { httpOnly: true });

    res.status(200).json({ message: 'Login berhasil', token });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Login gagal' });
  }
};

const logout = (req, res) => {
  // Hapus cookie token untuk logout
  res.clearCookie('token');
  res.status(200).json({ message: 'Logout berhasil' });
};

module.exports = {
  register,
  login,
  logout,
};
