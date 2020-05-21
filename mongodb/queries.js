/** To search */
db.grupoPoliticos.find();
db.votos.find();

/** To Drop collections **/
db.grupoPoliticos.drop();
db.votos.drop();

db.votos.aggregate([
    {"$group" : {_id:"$grupoPolitico", count:{$sum:1}}}
]);
   
// {"$unwind": "$grupoPolitico"}, 
// {"$match": {"grupoPolitico.nombre": "P1"}},
db.votos.aggregate([
    {"$group": {"_id": "$grupoPolitico", "votantes": {"$addToSet": "$dni"}, "cantidad": {"$sum": 1}}},
    {"$project": {"votantes": 1, "cantidad": 1, "grupoPolitico": "$_id"}}
]);
    
// this doesn't work
db.votos.aggregate([
    {
        $lookup: {
            from: "grupoPoliticos",
            localField: "grupoPolitico", // or author.$id
            foreignField: "_id",
            as: "grupoPolitico"
        }
    }
]);
    
// workaround stage 2
db.votos.aggregate([
    {
        $addFields: {
            "grupoPolitico1": {
                $arrayElemAt: [{ $objectToArray: "$grupoPolitico" }, 1]
            }
        }
    },
    {
        $addFields: {
            "grupoPolitico2": "$grupoPolitico1.v"
        }
    },
    {
        $lookup: {
            from: "grupoPoliticos",
            localField: "grupoPolitico['$id']", // or author.$id
            foreignField: "_id",
            as: "grupoPolitico"
        }
    }
]);
    
// workaround stage 2
db.votos.aggregate([
    {
        $addFields: {
            "grupoPolitico1": {
                $arrayElemAt: [{ $objectToArray: "$grupoPolitico" }, 1]
            }
        }
    },
    {
        $addFields: {
            "grupoPolitico2": "$grupoPolitico1.v"
        }
    },
    {
        $lookup: {
            from: "grupoPoliticos",
            localField: "grupoPolitico2", // or author.$id
            foreignField: "_id",
            as: "grupoPolitico"
        }
    },
    {
        $addFields: {
            "grupoPolitico": { $arrayElemAt: ["$grupoPolitico", 0] }
        }
    }
]);
    
// final workaround
db.votos.aggregate([
    {
        $addFields: {
            "grupoPolitico": {
                $arrayElemAt: [{ $objectToArray: "$grupoPolitico" }, 1]
            }
        }
    },
    {
        $addFields: {
            "grupoPolitico": "$grupoPolitico.v"
        }
    },
    {
        $lookup: {
            from: "grupoPoliticos",
            localField: "grupoPolitico", // or author.$id
            foreignField: "_id",
            as: "grupoPolitico"
        }
    },
    {
        $addFields: {
            "grupoPolitico": { $arrayElemAt: ["$grupoPolitico", 0] }
        }
    }
]);