const jwt = require('jsonwebtoken');

const checkAuth = (req, res, next) => {
  // Dapatkan token dari cookie atau header Authorization
  const token = req.cookies.token || req.headers.authorization;

  if (!token) {
    return res.status(401).json({ message: 'Token tidak ditemukan' });
  }

  try {
    // Verifikasi token
    const decodedToken = jwt.verify(token, 'secret_key');

    // Tambahkan informasi pengguna ke objek request
    req.userData = { userId: decodedToken.userId };

    // Lanjutkan ke middleware atau handler selanjutnya
    next();
  } catch (error) {
    console.error(error);
    return res.status(401).json({ message: 'Token tidak valid' });
  }
};

module.exports = {
  checkAuth,
};
