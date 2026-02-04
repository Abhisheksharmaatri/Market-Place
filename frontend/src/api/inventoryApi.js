import axios from "axios";
import SERVICE_URLS from "./config";

export const getInventory = async () => {
  const response = await axios.get(
    `${SERVICE_URLS.inventory}/inventory`
  );
  return response.data;
};


export const createInventory = async(inventory)=>{
  const response=await axios.post(
    `${SERVICE_URLS.inventory}/inventory`,
    inventory
  );
  return response.data;
}

export const updateInventory=async(inventory, id)=>{
  
  console.log("Data received in API helper:", inventory);

  // Destructure to be 100% sure we are using the fresh values
  const { productId, price, amount } = inventory;

  console.log(price, amount)

  const response = await axios.put(
    `${SERVICE_URLS.inventory}/inventory`,
    {
      productId, // Shorthand for productId: productId
      price,     
      amount
    },
    {
      params: { id },
      headers: {
        "Content-Type": "application/json"
      }
    }
  );
  return response.data;
}


export const deleteInventory=async(id)=>{
  const response=await axios.delete(
    `${SERVICE_URLS.inventory}/inventory`,
    {
      params:{id}
    }
  );
  return response.data;
}


export const getOneInventory=async(id)=>{
  const response=await axios.get(
    `${SERVICE_URLS.inventory}/inventory/item`,
    {
      params:{id}
    }
  );
  return response.data;
}

export const getOneInventoryByProductId=async(productId)=>{
  const response=await axios.get(
    `${SERVICE_URLS.inventory}/inventory/product`,
    {
      params:{productId}
    }
  );
  return response.data;
}
