const { History } = require('../models/History');

const getHistoryByUserId = async (req, res) => {
  try {
    const userId = req.userData.userId;

    // Dapatkan riwayat berdasarkan ID pengguna
    const history = await History.findAll({ where: { userId } });

    res.status(200).json(history);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal mendapatkan riwayat' });
  }
};

const addHistoryEntry = async (req, res) => {
  try {
    const userId = req.userData.userId;
    const { foodId } = req.body;

    // Tambahkan entri riwayat baru ke database
    const newHistoryEntry = await History.create({ userId, foodId });

    res.status(201).json({ message: 'Entri riwayat berhasil ditambahkan', history: newHistoryEntry });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Gagal menambahkan entri riwayat' });
  }
};

module.exports = {
  getHistoryByUserId,
  addHistoryEntry,
};
