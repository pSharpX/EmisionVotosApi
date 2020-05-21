db.votos.aggregate([
    {
        $lookup: {
            from: "grupoPoliticos",
            localField: "grupoPolitico", // or author.$id
            foreignField: "_id",
            as: "grupoPolitico"
        }
    },
    { $group: { _id: "$grupoPolitico.nombre", votantes: { $push: "$dni" }, cantidad: { $sum: 1 } } },
    { $unwind: "$_id" },
    { $project : { _id: 0, grupoPolitico: "$_id", votantes: 1, cantidad: 1} }
]);
    
// { $addFields: { grupoPolitico : { $sum: "$books.copies" } } },