namespace java de.hda.fbi.ds.myShopService // defines the namespace


typedef i32 int

service ShopService {
    string hello(1:string name),
    int getPriceByName(1:string name),
    int buyProduct(1:string name, 2:int value,3:int price),
}

