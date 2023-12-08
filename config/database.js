const { Sequelize } = require('sequelize');

// Konfigurasi koneksi database
const sequelize = new Sequelize('calofruit', 'root', '', {
  host: 'localhost',
  dialect: 'mysql',
  logging: false, // Set true untuk log query SQL ke konsol
});

// Coba koneksi ke database
sequelize
  .authenticate()
  .then(() => {
    console.log('Koneksi ke database berhasil');
  })
  .catch((err) => {
    console.error('Gagal terkoneksi ke database:', err);
  });

module.exports = sequelize;
