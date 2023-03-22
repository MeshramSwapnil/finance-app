export interface AuthRequest{
  username? : string;
  password? : string;
}

export interface ApiResponse{
  message : string;
  errorcode : number;
  status? : boolean;
  data? : any;
}

export interface AuthResponse{
  message : string;
  success : number;
  data? : any;
}


export interface Products{
  id : number;
	name : string;
	description : string;
	imageUrl? : string;
	charge : number;
  selected? : boolean;
}

export interface Currency{
	currency : string;
}

export interface CheckBook{
	id : number;
  pages : number;
  ratePerPage : number;
  description? : string
}


export interface Summary{
  pages : number;
  ratePerPage : number;
  description? : string;
  total : number;
  quantity : number;
  isCheckBook? : boolean;
}
